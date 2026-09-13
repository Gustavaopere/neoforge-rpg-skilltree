# Sable / Flowing Fluids Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db81ca9c3ec2cac016989c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sable_flowing_fluids_compat-1.0.2.jar`, mod id `sable_flowing_fluids_compat`, runtime `1.0.2`; Sable 2.0.5 e FlowingFluids 1.0.6 presentes; Sable Companion common 1.6.0 embarcado
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sable / Flowing Fluids Compat 1.0.2, Sable 2.0.5 e FlowingFluids 1.0.6 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable / Flowing Fluids Compat
- **Arquivo JAR:** `sable_flowing_fluids_compat-1.0.2.jar`
- **Versão 1.21.1:** 1.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Bridge de física de fluidos entre Sable e FlowingFluids: transfere corretamente o ownership quando fluido entra ou sai de physics objects/sublevels, incluindo suporte/catch points.
- **Dependências:** Stack físico: Sable 2.0.5 + FlowingFluids 1.0.6. O compat 1.0.2 inclui Sable Companion common 1.6.0 via Jar-in-Jar; não substitui nenhum provider.
- **Sobreposição:** Sable owns physics objects/sublevels; FlowingFluids owns fluido gerenciado; compat coordena a fronteira. Não é pipe/transport API universal e não deve duplicar hose bridges.
- **Compatibilidade/Riscos:** Riscos: dupe/loss de volume, stale ownership, moving-object race, partial-block leakage, catch-point/config scaling e double-processing com bridges/hoses. Implementação oficial usa APIs públicas, reduzindo mas não eliminando API drift.
- **Observações:** v1.0.2 corrige slabs/stairs/partial-height support e catch points. Projeto também documenta orientation-aware detach/attach, tilt gating e config em `sable_flowing_fluids_compat-common.toml`.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + CurseForge oficial v1.0.2 + README/source oficial Sable / Flowing Fluids Compat.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-flowing-fluids-compat ; https://www.curseforge.com/minecraft/mc-mods/sable-flowing-fluids-compat/files/8676265
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — dossiê 1.0.2 revalidado e ampliado: world↔sublevel fluid ownership, partial blocks, orientação/capsize, public-API scans, config, embedded Companion, lifecycle, riscos e testes.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sable_flowing_fluids_compat-1.0.2.jar`, mod id `sable_flowing_fluids_compat`, versão `1.0.2`. Esta bridge resolve a **transferência de ownership de fluidos entre o mundo Flowing Fluids e objetos/sublevels físicos do Sable**.

## 1. Identidade, versão e papel
- **Mod:** Sable / Flowing Fluids Compat.
- **JAR físico:** `sable_flowing_fluids_compat-1.0.2.jar`.
- **Mod id:** `sable_flowing_fluids_compat`.
- **Versão:** `1.0.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Papel:** corrigir transições de fluido entre o espaço/world grid e physics objects do Sable quando Flowing Fluids está ativo.

## 2. Stack físico confirmado
- **Sable:** `sable-neoforge-1.21.1-2.0.5.jar`.
- **FlowingFluids:** `flowing_fluids-1.0.6-1.21-neoforge.jar`.
- **Compat:** `sable_flowing_fluids_compat-1.0.2.jar`.

A modlist física é authority dessas versões.

## 3. Authority e ownership
A bridge existe porque um mesmo volume de fluido pode mudar de contexto físico:
- enquanto está sobre/dentro de um objeto Sable, precisa ser tratado como parte do contexto daquele physics object;
- quando flui para fora do objeto, deixa de ser tracked como parte dele e volta a ser fluido ordinário do mundo;
- quando fluido ordinário cai/entra sobre um physics object, deve passar a ser associado ao objeto conforme a geometria e suporte detectados.

Sable continua authority dos objetos/sublevels; Flowing Fluids continua authority da física/propagação de fluido no mundo. O compat só coordena a fronteira.

## 4. Problema funcional corrigido
A descrição oficial afirma que, antes da bridge, água/fluido que saía de um physics object podia quebrar o tracking, e fluido do mundo que alcançava o objeto não era corretamente incorporado ao contexto físico.

O objetivo não é criar novos fluidos, tanks ou pipes: é corrigir transformação e continuidade do mesmo fluido ao cruzar dois domínios de simulação.

## 5. Correções específicas da v1.0.2
A release 1.0.2 documenta:
- correção de fluidos atravessando **slabs, stairs e outros blocos de altura parcial** em objetos Sable;
- correção do suporte de fluido e da detecção de **catch points** para partial blocks;
- reconhecimento correto de blocos que impedem fluxo, incluindo slabs, stairs e full blocks.

Esses pontos são regression gates centrais desta versão.

## 6. Client / server e sincronização
Como a posição/ownership do fluido afeta mundo e physics objects, o estado precisa ser consistente no servidor e refletido no cliente.
- servidor deve determinar transições válidas e state físico;
- cliente renderiza o resultado sem inventar volume adicional;
- em multiplayer, dois clientes não podem observar o mesmo fluido simultaneamente como pertencendo ao mundo e ao sublevel de maneiras contraditórias.

## 7. Lifecycle
Validar:
- montagem/criação de physics object com fluido sobre ele;
- fluido entrando no objeto a partir do mundo;
- fluido saindo do objeto e voltando ao world grid;
- movimento/rotação do objeto durante fluxo;
- chunk load/unload;
- dimensão/relog/restart;
- alteração de bloco de suporte enquanto há fluido presente;
- desmontagem/destruição do objeto com fluido ainda associado.

## 8. Geometria e partial blocks
A v1.0.2 torna slabs/stairs/partial-height blocks parte explícita do escopo. Os testes devem cobrir:
- slab inferior e superior;
- stairs em orientações diferentes;
- bloco completo adjacente;
- gap/catch point real;
- fluxo lateral, queda vertical e retenção sobre superfície parcial.

Não presumir comportamento de qualquer shape customizado modded sem teste; a release só nomeia explicitamente as famílias acima.

## 9. Integrações concretas no pack
- **Sable 2.0.5:** provider de physics objects/sublevels.
- **FlowingFluids 1.0.6:** provider de comportamento de fluido contínuo.
- **Create Aeronautics 1.3.2 e addons Sable:** aumentam cenários com estruturas móveis contendo fluidos.
- **VS Sable Hose Connectors:** outro caminho de transferência de fluido; precisa de teste para evitar double-transfer ou ownership conflitante.
- Qualquer Create fluid transport continua tendo seu próprio handler; esta bridge não deve ser tratada como pipe API universal.

## 10. Implementação, orientação e configuração
O projeto oficial documenta que a bridge evita depender dos loops internos privados de ambos os mods e trabalha por **scans periódicos usando APIs públicas** de Sable e Flowing Fluids. O JAR físico inclui `Sable Companion 1.6.0` via Jar-in-Jar, coerente com essa estratégia de compatibilidade.

No sentido outbound, o suporte do fluido considera a direção local equivalente à gravidade real; ao inclinar/capsizar o objeto, fluido sem caminho de suporte pode sair, enquanto um volume realmente selado permanece contido. No sentido inbound, pontos de captura de superfícies abertas são cacheados e verificados contra a pose atual do sublevel; absorção é bloqueada além de um limite configurável de inclinação para evitar que um navio virado absorva o oceano sob o casco.

A configuração comum fica em `config/sable_flowing_fluids_compat-common.toml` e expõe toggles/intervalos/caps para attach/detach, profundidade máxima de scan, frequência de rescan estrutural, limite de catch points e ângulo máximo de inclinação. Os defaults publicados pertencem ao projeto e não foram tratados como config local efetivamente escolhida pelo pack sem abrir o arquivo da instância.

A bridge só processa fluidos que `FlowingFluidsAPI` declara gerenciar; comportamento vanilla-only permanece fora de seu ownership.

## 11. Riscos técnicos
1. **Dupe/loss de volume:** transição world↔object pode contar o mesmo fluido duas vezes ou perder volume.
2. **Partial block leakage:** principal regression da 1.0.2.
3. **Stale ownership:** unload/reload pode deixar fluido associado ao contexto errado.
4. **Moving object race:** movimento durante flow update pode produzir transferência em coordenada antiga.
5. **Double-processing:** outros fluid bridges/hoses podem interceptar a mesma transferência.
6. **Custom shapes:** blocos modded com voxel shapes complexos precisam de validação individual.

## 12. Matriz de testes
- [ ] Sable 2.0.5 + FlowingFluids 1.0.6 + compat 1.0.2 iniciam em dedicated server.
- [ ] Água do mundo cai sobre physics object e passa a acompanhá-lo corretamente.
- [ ] Água flui para fora do objeto e vira world fluid sem duplicar/perder volume.
- [ ] Slab inferior/superior bloqueia/sustenta conforme geometria.
- [ ] Stairs em quatro orientações.
- [ ] Full blocks interrompem fluxo corretamente.
- [ ] Catch points parciais retêm/transferem corretamente.
- [ ] Objeto se move/rota enquanto contém fluido.
- [ ] Chunk unload/reload e server restart sem stale fluid.
- [ ] Hose/pipe interactions sem double settlement.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 13. Evidências
- Modlist física canônica de 10/09/2026: Sable 2.0.5, FlowingFluids 1.0.6, compat 1.0.2 e Sable Companion common 1.6.0 embarcado.
- CurseForge oficial Sable & Flowing Fluids Compat 1.0.2: ownership world↔physics object e changelog de partial blocks/catch points.
