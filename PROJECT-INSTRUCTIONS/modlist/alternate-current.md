# Alternate Current

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db81ba9473edd0d2e571af
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Alternate Current
- **Arquivo JAR:** `alternate_current-mc1.21-1.9.0.jar`
- **Versão 1.21.1:** 1.9.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:**
- **Categoria:**
- **Função:** Otimização server-side do algoritmo de redstone wire vanilla: recalcula redes conectadas, reduz power checks/neighbor/shape updates redundantes e aplica ordem determinística configurável.
- **Dependências:** Minecraft/NeoForge compatíveis. Não adiciona hard dependency de gameplay confirmada. Integra por comportamento com qualquer provider/consumer de redstone; Create/Aeronautics/Sable exigem regressão por alterarem contextos móveis/sublevels.
- **Sobreposição:**
- **Compatibilidade/Riscos:** Circuitos sensíveis à ordem vanilla podem divergir; upstream 1.9.0 tem regression gates para Gimbal Sensor/Aeronautics e redstone em Y=319. Testar BlockEntity-only signal, chunk boundaries, pistons/observers/comparators e config update-order. Não afirmar bugs locais sem teste.
- **Observações:** mod id `alternate_current`; runtime 1.9.0. Sem conteúdo de gameplay próprio confirmado. Config por mundo `alternate-current.conf`; comando `/alternatecurrent updateOrder`. Valores efetivos da config do usuário não foram lidos.
- **Procedência:** modlist.txt física atual de 08/09/2026 + Modrinth/GitHub oficiais Alternate Current 1.9.0 + dossiê operacional existente.
- **Fonte:** https://modrinth.com/mod/alternate-current/version/neoforge-mc1.21-1.9.0 ; https://github.com/SpaceWalkerRS/alternate-current
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — redstone wire authority, deterministic update order, per-world config, Gimbal/Y=319 regression gates and lifecycle confirmed in global QC #24.
- **Histórico da decisão:**
- **Data da última decisão:**

> ⚙️ **ESCOPO CANÔNICO.** Runtime físico: `alternate_current-mc1.21-1.9.0.jar`, mod id `alternate_current`, versão `1.9.0`, Minecraft/NeoForge 1.21.1. Esta ficha trata Alternate Current como **substituição/otimização do algoritmo de redstone wire**, não como provider de novos blocos ou itens.

## 1. Identidade, versão e papel
- **Mod:** Alternate Current.
- **JAR físico:** `alternate_current-mc1.21-1.9.0.jar`.
- **Mod id:** `alternate_current`.
- **Versão instalada:** `1.9.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Papel no pack:** otimização server-side/singleplayer do processamento de redstone dust. A versão 1.9.0 é publicada para Minecraft 1.21–1.21.1 e adiciona uma nova rodada de otimizações.
- **Source/version pin:** release/documentação oficial 1.9.0 confirmada; commit exato do binário físico não foi pinado nesta auditoria.

## 2. Authority e ownership
Alternate Current assume a responsabilidade pelo **cálculo e pela propagação de potência da redstone wire vanilla** quando habilitado. Ele não passa a ser autoridade sobre consumidores de redstone, BlockEntities, sinais produzidos por outros mods ou lógica específica de Create/Sable: esses sistemas continuam responsáveis pelo próprio estado e apenas recebem/notificam mudanças de redstone.

A implementação evita a recursão vanilla de wire→wire. Em vez disso, constrói a rede conectada de redstone, identifica fontes externas, calcula a propagação e então aplica o estado final da rede. O objetivo é produzir menos verificações e menos neighbor/shape updates redundantes.

## 3. Conteúdo registrado
- **Novos blocos/itens/entities:** nenhum conteúdo de gameplay foi confirmado nem é parte do propósito publicado do mod.
- **Superfície principal:** alteração do comportamento interno da redstone wire vanilla.
- **Comando confirmado:** `/alternatecurrent updateOrder` permite consultar/alterar a ordem de updates.
- **Config por mundo:** `<world root>/alternate-current.conf`.

Não foram inventados registries auxiliares, events próprios ou data components sem source pin específico.

## 4. Algoritmo e sistemas internos
A documentação oficial descreve três frentes principais:
1. **Cálculo de potência:** uma wire da rede verifica potência não proveniente de wire no máximo uma vez e wires vizinhas no máximo duas vezes durante o cálculo do estado da rede, evitando múltiplas alterações intermediárias do power level.
2. **Neighbor/shape updates:** a wire é escrita no mundo apenas com seu valor final; updates redundantes e estados intermediários são eliminados. A documentação upstream observa que a implementação vanilla pode emitir dezenas de updates por mudança de potência e que parte deles é desnecessária.
3. **Ordem determinística:** Alternate Current substitui a ordem locacional/inconsistente vanilla por uma ordem baseada na direção do fluxo de potência. Isso visa reduzir dependência da orientação física do circuito e corrige a classe de comportamento associada ao bug vanilla MC-11193.

A release 1.9.0 anuncia novas otimizações, com ganhos que variam conforme o circuito; valores upstream de benchmark não devem ser tratados como ganho garantido deste modpack.

## 5. Configuração
A linha 1.8+ documenta configuração por mundo em `alternate-current.conf`.
- `enabled`: habilita/desabilita Alternate Current naquele mundo.
- `update-order`: escolhe a ordem de neighbor updates.
- Ordens documentadas: `horizontal_first_outwards`, `horizontal_first_inwards`, `vertical_first_outwards`, `vertical_first_inwards`.

A configuração efetiva do mundo do usuário **não foi lida nesta auditoria**; portanto os valores atuais não são afirmados.

## 6. Client / server
- A lógica de redstone é **server-authoritative**. Em singleplayer, ocorre no servidor integrado.
- Não há gameplay client-only a documentar; o cliente observa os estados sincronizados pelo servidor.
- Instalação/versão deve ser avaliada principalmente pela estabilidade do lado servidor e compatibilidade de circuitos.

## 7. Lifecycle
Validar especialmente:
- criação/carregamento do mundo e leitura da config por mundo;
- `/alternatecurrent updateOrder` durante runtime;
- save/reload e server restart preservando comportamento;
- chunk load/unload com redes atravessando bordas;
- pistons/observers/repeaters/comparators e BlockEntities que geram sinal sem necessariamente trocar BlockState;
- estruturas móveis/sublevels de Sable/Create Aeronautics que possam transportar produtores/consumidores de redstone.

## 8. Multiplayer
O estado relevante pertence ao mundo/servidor, não ao jogador. Em multiplayer, todos os jogadores devem observar o mesmo estado final de redstone. Os testes devem procurar divergência de atualização, circuitos que fiquem presos em estado antigo e diferença entre jogador local/remoto ao acionar o mesmo circuito.

## 9. Integrações concretas no pack
- **Create 6.0.10:** máquinas, redstone links, observers e outros componentes continuam sendo providers/consumers próprios; testar circuitos mistos sem assumir integração especial.
- **Create Aeronautics/Sable:** há report upstream em 1.9.0 envolvendo Gimbal Sensor em estrutura física, cujo valor era armazenado na BlockEntity sem BlockState mudar e acabava não propagando como esperado. Isto é **risco upstream**, não bug local confirmado.
- O mod é complementar a otimizações gerais como ServerCore; eles atuam em subsistemas diferentes.

## 10. Riscos técnicos
1. **Compatibilidade semântica:** circuitos que dependam de quirks de ordem de update vanilla podem mudar comportamento, apesar do objetivo de equivalência funcional.
2. **BlockEntity-only signal:** providers cujo sinal muda sem mudança de BlockState precisam ser regression-tested, especialmente em sublevels/contraptions.
3. **Limite de altura:** há issue upstream da linha 1.9.0 sobre redstone em Y=319; uma correção foi registrada posteriormente em 1.9.1 para outra faixa de Minecraft. Para este runtime 1.21.1/1.9.0, tratar como regression gate até teste local.
4. **Chunk boundaries:** rede atravessando chunks pode revelar stale state após unload/reload.
5. **Config drift:** mudar `update-order` pode alterar circuitos sensíveis à sequência de neighbor updates.

## 11. Matriz de testes
- [ ] Dedicated server boot com 1.9.0.
- [ ] Circuitos simples on/off e analógicos 0–15.
- [ ] Repeater/comparator/observer/piston/TNT sem dupla execução.
- [ ] Circuito equivalente em quatro orientações para detectar dependência locacional.
- [ ] Rede longa cruzando borda de chunk; unload/reload e restart.
- [ ] Redstone em Y=319 e níveis próximos.
- [ ] Create Redstone Link e máquinas ativadas exatamente uma vez.
- [ ] Gimbal Sensor/outputs em estrutura Aeronautics/Sable, incluindo mudança de sinal sem BlockState.
- [ ] Troca de `update-order` via comando e persistência após restart.
- [ ] Dois jogadores acionando a mesma rede sem desync.

Nenhum destes testes foi declarado aprovado nesta auditoria.

## 12. Evidências
- Modlist física canônica de 08/09/2026: JAR/mod id/versão.
- Modrinth oficial: release NeoForge 1.9.0 para Minecraft 1.21–1.21.1.
- GitHub/README e release notes oficiais Alternate Current: arquitetura da rede, redução de checks/updates, ordem determinística, config e comando.
- Issues upstream 1.9.0: Gimbal Sensor/Aeronautics e redstone em Y=319, registradas somente como riscos de regressão.
