# Create Cobblestone

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8141a65ed65d86cb2566
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Cobblestone
- **Arquivo JAR:** `createcobblestone-1.5.0+neoforge-1.21.1-153.jar`
- **Versão 1.21.1:** 1.5.0+neoforge-1.21.1-153
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Automação, Tecnologia, Performance
- **Função:** Adiciona gerador de cobblestone acionado por stress/rotação Create, substituindo farms vanilla de água/lava por uma solução mecânica mais controlada e voltada a reduzir lag.
- **Dependências:** Create obrigatório; source oficial 1.5.0 para MC 1.21.1 declara compatibilidade com a linha Create 6.0.x até antes da 6.1. Pack físico usa Create 6.0.10.
- **Sobreposição:** Compartilha geração renovável de stone/cobblestone com outras rotas, mas sua superfície específica é máquina Create configurável/data-driven orientada a reduzir custo de tick. Comparar SU, throughput e MSPT real.
- **Compatibilidade/Riscos:** Riscos: throughput/config trivializa recursos; production double-count em speed/reload; storage overflow; generator type removido/desabilitado em mundo existente; custom datapack extremo; Create API/stress drift; ganho de performance não comprovado sem profiling.
- **Observações:** Runtime físico preservado integralmente: `1.5.0+neoforge-1.21.1-153`. Source branch `1.21.1-neoforge` declara mod base 1.5.0; README confirma 5 generator types, configs e datapacks custom.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + runtime completo + repositório oficial StickyPiston-development/CreateCobblestone branch 1.21.1-neoforge.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-cobblestone
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê da build 1.5.0+...-153 com generator types, RPM↔SU rates, config, custom datapacks, storage, reload/migration e performance gates catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> 🪨 **Identidade física confirmada:** `createcobblestone-1.5.0+neoforge-1.21.1-153.jar`, mod id `createcobblestone`, runtime `1.5.0+neoforge-1.21.1-153`. O branch oficial 1.21.1-NeoForge declara mod base 1.5.0 e Create range anterior a 6.1.

## 1. Papel e authority
Create Cobblestone substitui farms vanilla de geração por um **Mechanical Generator** movido por stress/rotação Create, com objetivo explícito de reduzir entidades/updates e custo de lag de geradores tradicionais. O addon owns generator state, tipos, storage interno e regras de produção; Create owns RPM/stress.

## 2. Generator types padrão
A documentação oficial confirma cinco tipos padrão:
- Cobblestone;
- Stone;
- Basalt;
- Limestone;
- Scoria.

O tipo do generator muda ao interagir com um item válido. O block/item provider correspondente continua owner do recurso produzido.

## 3. Deepslate via datapack
Deepslate e Cobbled Deepslate existem como opções associadas a datapack separado e ficam desabilitadas por padrão. Não são parte automática da configuração base apenas porque o código conhece os tipos.
World/datapack state é authority para sua disponibilidade.

## 4. Escala por RPM
Com os defaults documentados, cada **8 RPM** adiciona 1 unidade/tick e cada RPM adiciona **8 SU** de stress; isso equivale a 64 SU por unidade/tick no exemplo oficial.
Esses valores são defaults de projeto, não garantia da configuração local: config e overrides por generator type prevalecem.

## 5. Config de rates
Stress por RPM e RPM necessários por unidade/tick são configuráveis. Alterar esses parâmetros muda diretamente throughput e custo cinético.
Integrações/quests não devem hardcodar o default se o pack possuir config customizada.

## 6. Enable/disable de tipos
Tipos podem ser habilitados/desabilitados via config. A documentação afirma que desabilitar um generator que já está sendo usado no mundo o substitui por um generator vazio.
Isso é um migration boundary: mudança de config precisa ser testada em cópia de mundo para evitar perda inesperada de state/item.

## 7. Custom generators por datapack
O addon aceita tipos customizados por datapack em `data/<mod>/generator_types/<type>.json`. O campo obrigatório é `block`; os opcionais publicados são `stress`, `ratio` e `storage`.
Per-generator config sobrepõe o default global correspondente, então balanceamento deve consultar o JSON efetivamente carregado.

## 8. Storage
Custom generator types podem definir `storage`. Isso indica buffering interno configurável na arquitetura publicada.
Extração/inserção e overflow precisam seguir o state real do generator; não presumir inventory capability genérica sem source específico da build.

## 9. Produção e conservation
Produção deve depender de RPM/stress válido e respeitar o storage. Mudança rápida de speed, overstress, chunk unload ou output bloqueado não pode contabilizar produção duas vezes.
A unidade produzida é resource generation intencional; qualquer loop que converta output em stress suficiente para gerar mais output merece teste econômico separado.

## 10. Objetivo de performance
O projeto é explicitamente orientado a reduzir lag frente a geradores de cobblestone baseados em água/lava + break/pickup. O ganho real depende de factory layout, número de blocks e versão Create.
Avaliar MSPT/tick cost, não FPS apenas, e comparar cenários equivalentes.

## 11. Recipe e balanceamento
O upstream reconhece que o mod facilita geração de cobblestone e recomenda ajustar recipe/config via datapack/KubeJS se o pack considerar o custo baixo demais.
A catalogação não altera esse balanceamento; apenas registra que existe uma superfície explícita de ajuste.

## 12. Compatibilidade Create
O source 1.5.0 declara Create 6.0.0-9 como ambiente de desenvolvimento e limite `<6.1`; o pack usa Create 6.0.10. É a mesma linha 6.0.x, mas smoke-test continua necessário porque o build físico inclui sufixo NeoForge/build 153.

## 13. Client/server
RPM, stress, generator type, storage e produção são server-authoritative. Models/animation/tooltip são client-facing.
Um cliente não pode produzir recurso apenas por renderizar a rotação ou alterar tooltip/config local.

## 14. Reload e data lifecycle
Adicionar/remover generator type por datapack e executar `/reload` precisa reconstruir a tabela de tipos com fallback seguro para generators existentes. Type ID removido não deve gerar crash loop ou output fantasma.

## 15. Chunk/restart lifecycle
Generator ativo, buffer parcialmente cheio e network overstressed são cenários de persistência. Restart/unload não pode repetir produção acumulada indevidamente nem perder tipo configurado.

## 16. Sobreposição
O pack possui outras rotas de stone/cobblestone/resource renewal. O overlap relevante é econômico/performance, não necessariamente duplicidade funcional: este addon oferece uma máquina cinética configurável e data-driven.
Comparar custo em SU, recipe e throughput real antes de remover.

## 17. Riscos
1. Config muito barata trivializa materiais de construção.
2. Loop output→energia→RPM gera recurso líquido excessivo.
3. Speed change contabiliza produção duas vezes.
4. Storage overflow duplica ou perde output.
5. Disable de generator type transforma bloco existente e perde state inesperadamente.
6. Datapack remove type usado por mundo existente.
7. `/reload` mantém type cache stale.
8. Custom JSON com stress/ratio extremos causa overflow/performance issue.
9. Create update altera stress behavior esperado.
10. Benefício de performance é assumido sem profiling do pack.

## 18. Matriz de testes
- [ ] Dedicated server inicia com build 1.5.0+...-153 + Create 6.0.10.
- [ ] Cobblestone/Stone/Basalt/Limestone/Scoria types funcionam conforme config.
- [ ] 8 RPM/default produz o throughput documentado apenas se config local estiver default.
- [ ] Overstress/zero RPM interrompem produção sem saldo fantasma.
- [ ] Storage cheio não duplica output.
- [ ] Chunk unload/restart preserva type/buffer.
- [ ] Disable de type em mundo de teste produz o fallback documentado.
- [ ] Datapack custom registra block/stress/ratio/storage corretamente.
- [ ] `/reload` adiciona/remove type sem crash/state stale.
- [ ] Deepslate não aparece sem datapack habilitado.
- [ ] Profiling compara MSPT com generator vanilla equivalente.
- [ ] Rotas do pack não criam loop econômico positivo usando o output.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências e limites
A modlist física confirma artefato/runtime completo com build suffix. O repositório oficial branch `1.21.1-neoforge` declara mod 1.5.0 e MC 1.21.1; README confirma tipos, config, custom datapacks e default 8 RPM/8 SU. Config local do pack não foi lida nesta etapa, portanto defaults são rotulados como defaults, não policy atual confirmada.

> 🔒 **Boundary canônico:** Create supplies RPM/stress; Create Cobblestone owns a conversão cinética em geração de resource. Throughput/custo devem sempre vir da config/type carregados, e o principal valor técnico do addon é reduzir o custo de tick de farms tradicionais.
