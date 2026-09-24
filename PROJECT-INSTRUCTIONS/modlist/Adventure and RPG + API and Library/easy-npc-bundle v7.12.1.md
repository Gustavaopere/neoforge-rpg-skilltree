# Easy NPC: Bundle

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#240**: JAR `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc_bundle`, runtime `7.12.1`, SHA-1 `2aaf47e6d2f586b8e7bafaf12b7c1f18eedd4d65`.

## Propriedades do registro

- **Mod:** Easy NPC: Bundle
- **Arquivo JAR:** `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar`
- **Versão 1.21.1:** `7.12.1`
- **Categoria:** RPG, QoL
- **Função:** Meta-package/bundle da família Easy NPC que declara/organiza a instalação alinhada do Core e Config UI; não implementa um segundo sistema de NPC nem substitui os módulos funcionais.
- **Dependências:** Easy NPC Core + Easy NPC Config UI; NeoForge 1.21.1. No pack atual, Core, Bundle e Config UI existem como três JARs top-level separados e alinhados pelo filename/release 7.12.1.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Risco principal é misturar versões diferentes da família ou interpretar Bundle como provider de gameplay. A arquitetura moderna evita jar-in-jar justamente para não produzir mod IDs duplicados, classloading ambíguo e dependências que enxergam apenas o Core.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/all
- **Procedência:** modlist física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc_bundle` e runtime `7.12.1`, junto de Core/Config UI na mesma linha.
- **Observações:** Bundle 7.12.1 está fisicamente alinhado ao Core/Config UI 7.12.1. O meta-package continua com semântica de distribuição/dependência; gameplay e persistência pertencem ao Core.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #239: Bundle atualizado fisicamente para `7.12.1`, alinhado a Core/Config UI da mesma linha; continua sendo meta-package/dependency aggregation, não provider de NPC state.
- **Decisão:** Dependência
- **Sobreposição:** Não é duplicata funcional do Easy NPC Core. A sobreposição de nome é de distribuição: Bundle agrega dependências, Core fornece NPCs, Config UI fornece a interface.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar` · mod id `easy_npc_bundle` · versão `7.12.1` · NeoForge 1.21.1.
## 1. Papel no modpack
Easy NPC: Bundle é um **meta-package de distribuição** da família Easy NPC. Sua função é facilitar instalação e alinhamento de dependências, não criar NPCs, diálogos, trades ou state próprio equivalente ao Core.
## 2. Arquitetura moderna do bundle
A documentação oficial do projeto esclarece que o Bundle moderno **não contém os módulos Easy NPC dentro do próprio JAR**. Em vez disso, ele declara dependências para instalar os módulos necessários separadamente.
Essa arquitetura evita os problemas do antigo modelo jar-in-jar: mod IDs duplicados, classloading ambíguo e mods externos detectando apenas parte da família.
## 3. Dependências funcionais
O Bundle organiza:
- **Easy NPC Core** — provider real de NPC state/gameplay;
- **Easy NPC Config UI** — ferramentas gráficas e networking de configuração.
No pack atual os três aparecem como JARs top-level separados, alinhados na linha 7.12.1 por filename/build/publicação; a metadata runtime do Core continua não declarada.
## 4. Authority / ownership
- **Bundle:** relação de dependência/distribuição.
- **Core:** NPCs, persistência, dialogs, actions, trades e behavior.
- **Config UI:** screens/config flow/networking correspondente.
Nenhuma integração deve consultar o Bundle para obter NPC state quando o contrato pertence ao Core.
## 5. Client / Server
O Bundle pode estar presente nos dois lados por causa de dependency resolution do modpack, mas não deve ser tratado como gameplay authority. Client/server contracts reais vêm dos módulos que ele exige.
## 6. Lifecycle
O ponto crítico é o **bootstrap/dependency graph**:
- todas as dependências exigidas devem estar presentes;
- versões da família devem permanecer compatíveis;
- não carregar duas cópias do mesmo módulo;
- atualização do Bundle deve ser acompanhada de verificação do Core/Config UI efetivamente resolvidos.
## 7. Atualização e version alignment
Misturar Bundle com módulos de outra linha pode criar API/GUI/network drift mesmo que o launcher permita boot. No pack atual, Bundle, Config UI e filename/build do Core estão alinhados em **7.12.1**.
A coluna física de versão do Core continua vazia; o alinhamento da família não autoriza preencher metadata runtime ausente por inferência.
A linha instalada 7.12.1 deve ser tratada coordenadamente: atualizar apenas o Bundle ou apenas um módulo reabre risco de API/GUI/network drift.
## 8. Riscos
1. interpretar Bundle como segundo sistema de NPC;
2. duplicar conteúdo do Core na documentação/integração;
3. versões da família desalinhadas;
4. dependência ausente;
5. duas cópias do mesmo módulo;
6. voltar a usar jar-in-jar e criar mod ID duplicado;
7. classloading/handshake divergente entre cliente e servidor;
8. remover Core/Config UI porque o Bundle parece 'completo'.
## 9. Matriz de testes
1. Dedicated server boot com Core + Bundle + Config UI 7.12.1.
2. Client connect com o mesmo conjunto.
3. Confirmar presença única de cada mod id.
4. Abrir Config UI e editar NPC do Core.
5. Reiniciar servidor e confirmar persistência.
6. Atualizar a família em cópia de teste e verificar dependency resolution.
7. Remover uma dependência em instância descartável e confirmar falha clara, não comportamento parcial silencioso.
**Esta catalogação não afirma que esses testes foram executados.**
## 10. Evidências
- modlist física atual de 21/09/2026: Bundle `7.12.1` + Core build `7.12.1` + Config UI `7.12.1` como JARs top-level;
- documentação oficial Easy NPC Bundle: pacote de conveniência, módulos separados por dependência e abandono do antigo bundle jar-in-jar.
> **Boundary canônico:** Bundle possui **dependency/distribution semantics**. NPC gameplay pertence ao Core; edição pertence ao Config UI.
