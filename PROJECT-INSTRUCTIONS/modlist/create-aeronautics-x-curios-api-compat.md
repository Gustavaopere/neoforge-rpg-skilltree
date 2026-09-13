# Create Aeronautics x Curios API Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81f3ab69df5e6e648a60
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics x Curios API Compat
- **Arquivo JAR:** `createaeronauticscurios-neoforge-1.21.1-2.2.jar`
- **Versão 1.21.1:** 2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia, QoL
- **Função:** Integra Create Aeronautics com Curios API para permitir o uso funcional de Aviator Goggles e Linked Typewriter em slots Curios, incluindo ativação remota do Linked Typewriter e comportamento dos goggles quando equipados.
- **Dependências:** Create Aeronautics 1.3.2 + Curios API 9.5.1+1.21.1 estão fisicamente presentes; Create 6.0.10 compõe o stack.
- **Sobreposição:** Não duplica Create: Jetpack Curios nem Create SA Curios Jetpacks; atende itens próprios do Create Aeronautics. Também não é o mesmo JAR do projeto separado focado em Linked Typewriter.
- **Compatibilidade/Riscos:** Divergência preservada: filename/publicação 2.2, metadata runtime 2.0. Riscos: dependency/version checks, Curios slot/tag drift, perda de link do Typewriter, double remote activation, keybind conflict e Aeronautics API drift.
- **Observações:** JAR físico `createaeronauticscurios-neoforge-1.21.1-2.2.jar`, mod id runtime `aeronautics_curios_compat`, nome runtime Create Aeronautics Curios Compat, versão metadata 2.0; publicação oficial é 2.2 Release NeoForge 1.21.1 de 31/07/2026.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Create Aeronautics x Curios API Compat 2.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-x-curios-api-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; Aviator Goggles, Linked Typewriter remoto, Curios ownership, data persistence e version mismatch catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🥽 **ESCOPO CANÔNICO.** Artefato físico: `createaeronauticscurios-neoforge-1.21.1-2.2.jar`. A publicação pública é **2.2**, mas o metadata/runtime extraído do JAR declara versão **2.0** e mod id `aeronautics_curios_compat`. As duas identidades são preservadas; não normalizar uma sobre a outra sem corrigir o próprio artefato.

## 1. Função da bridge
O mod adiciona suporte Curios para dois itens de Create Aeronautics:
- **Aviator Goggles** no head Curios slot;
- **Linked Typewriter** em slot Curios dedicado.

Curios continua owner do accessory inventory; Aeronautics continua owner da semântica dos itens.

## 2. Aviator Goggles
Quando equipados no slot de cabeça Curios, os goggles devem ser reconhecidos pelo ecossistema Create para overlay e engineer detection como se estivessem no local esperado pelo provider. O renderer/overlay é apresentação; o equip state relevante deve ser lido do Curios state autoritativo.

## 3. Linked Typewriter remoto
O fluxo documentado é:
1. configurar keybindings no bloco físico;
2. coletar o Linked Typewriter;
3. colocá-lo no slot Curios dedicado;
4. usar a tecla remota, `Y` por default e rebindable.

A ativação remota se comporta como right-click no bloco original e envia sinais aos Redstone Links conectados. Isso torna identity/link state do item crítico para persistência.

## 4. Runtime físico
- Create Aeronautics 1.3.2.
- Curios 9.5.1+1.21.1.
- Create 6.0.10.

O file público 2.2 é Release NeoForge 1.21.1 Client & Server. A divergência `filename/publicação=2.2` versus `metadata=2.0` é um fato local e deve permanecer visível em diagnósticos.

## 5. Client/server boundary
Keybind e overlay são client-facing, mas equip state e efeito da ativação remota precisam convergir no servidor. Não executar Redstone Link action apenas porque o cliente reportou tecla pressionada; o item/slot/link deve ser validado no lado autoritativo.

## 6. Persistência e identidade
O Linked Typewriter precisa preservar o vínculo configurado ao ser movido de bloco→item→Curios→relog. Copiar/reconstruir stack sem seus data components/NBT pode apagar o link ou gerar item que aponta para alvo stale.

## 7. Sobreposição
Não é duplicata de bridges de jetpack/backtank. Seu escopo oficial é Aviator Goggles + Linked Typewriter. Outros mods de Curios podem usar os mesmos slots, mas isso é compatibilidade de inventory layout, não equivalência funcional.

## 8. Riscos
1. Metadata 2.0 confunde dependency/version checks enquanto filename é 2.2.
2. Goggles equipados não são detectados pelo Create overlay.
3. Linked Typewriter perde link ao ser movido para Curios.
4. Remote activation dispara duas vezes client/server.
5. Keybind conflitante com outros mods.
6. Curios slot/tag muda após update.
7. Aeronautics API drift muda identificação do item.

## 9. Matriz de testes
- [ ] Dedicated server inicia com artefato 2.2 / metadata 2.0.
- [ ] Aviator Goggles em head Curios exibem overlay e engineer detection.
- [ ] Retirar goggles remove o efeito sem cache stale.
- [ ] Linked Typewriter preserva configuração após pickup/relog.
- [ ] Tecla remota ativa exatamente uma vez e envia Redstone Link correto.
- [ ] Cliente sem item/slot válido não consegue forçar ativação.
- [ ] Key rebinding funciona e não altera server state indevidamente.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limite
CurseForge oficial confirma Release/publicação 2.2 e o funcionamento de Aviator Goggles/Linked Typewriter. A modlist física confirma que o mesmo artefato declara runtime 2.0. Esta ficha não inventa a causa do version mismatch; ele permanece como risco de packaging/metadata.
